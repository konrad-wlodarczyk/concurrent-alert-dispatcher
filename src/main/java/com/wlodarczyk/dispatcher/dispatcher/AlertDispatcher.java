package com.wlodarczyk.dispatcher.dispatcher;

import com.wlodarczyk.dispatcher.model.Alert;
import com.wlodarczyk.dispatcher.model.Unit;
import com.wlodarczyk.dispatcher.model.enums.AlertType;
import com.wlodarczyk.dispatcher.model.enums.UnitType;
import com.wlodarczyk.dispatcher.repository.AlertRepository;
import com.wlodarczyk.dispatcher.repository.UnitRepository;
import com.wlodarczyk.dispatcher.service.DispatchService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AlertDispatcher {

    private final PriorityBlockingQueue<Alert> queue = new PriorityBlockingQueue<>(
            1000,
            Comparator.comparing(Alert::getPriority)
    );
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final DispatchService dispatchService;

    public AlertDispatcher(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    public void enqueue(Alert alert) {
        queue.offer(alert);
    }

    @PostConstruct
    public void start() {
        Thread.ofVirtual().start(() -> {
            while (true) {
                try {
                    Alert alert = queue.take();
                    CompletableFuture.runAsync(() -> dispatchService.dispatch(alert), executorService)
                            .exceptionally(ex -> {
                                System.err.println("Dispatch failed: " + ex.getMessage());
                                return null;
                            });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
