package com.ecagataydogan.notificationservice.listener;

import com.ecagataydogan.notificationservice.dto.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void sendNotification(NotificationDTO notificationDTO) {

        log.info("read from queue: {}", notificationDTO);

    }


}
