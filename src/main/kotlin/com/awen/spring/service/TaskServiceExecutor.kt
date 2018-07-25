package com.awen.spring.service

import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service("taskService")
class TaskServiceExecutor: TaskService {

    private val executorService = Executors.newFixedThreadPool(5)

    override fun run(runnable: Runnable) {
        executorService.submit(runnable)
    }

}