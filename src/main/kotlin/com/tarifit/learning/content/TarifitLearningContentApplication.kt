package com.tarifit.learning.content

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class TarifitLearningContentApplication

fun main(args: Array<String>) {
    runApplication<TarifitLearningContentApplication>(*args)
}