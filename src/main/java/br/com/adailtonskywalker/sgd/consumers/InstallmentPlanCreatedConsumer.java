package br.com.adailtonskywalker.sgd.consumers;

import br.com.adailtonskywalker.sgd.messaging.Queues;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import br.com.adailtonskywalker.sgd.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstallmentPlanCreatedConsumer {
    private final InstallmentService installmentService;

    @RabbitListener(queues = Queues.INSTALLMENT_PLAN_CREATED)
    public void consume(InstallmentPlan installmentPlan) {
        log.info("Installment Plan created: {}", installmentPlan);
        log.info("Installment Plan created: {}", installmentPlan.getQuantity());
        installmentService.createByInstallmentPlan(installmentPlan);
    }
}