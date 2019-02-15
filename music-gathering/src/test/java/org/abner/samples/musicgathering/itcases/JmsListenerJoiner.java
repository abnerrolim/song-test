package org.abner.samples.musicgathering.itcases;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.concurrent.CountDownLatch;

@Aspect
@NotThreadSafe
public class JmsListenerJoiner implements Ordered {
    private CountDownLatch countDown;

    public JmsListenerJoiner() {
        reinitialize(1);
    }

    @Around("!within(is(FinalType)) && @annotation(org.springframework.jms.annotation.JmsListener)")
    public void decorateAnnotatedListener(final ProceedingJoinPoint pjp) {
        doCountdown(pjp);
    }

    private void doCountdown(final ProceedingJoinPoint pjp) {
        try {
            pjp.proceed();
            countDown.countDown();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void await() {
        try {
            countDown.await();
            reinitialize(1);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private void reinitialize(final int count) {
        countDown = new CountDownLatch(count);
    }
}