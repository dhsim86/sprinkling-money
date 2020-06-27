package com.kakaopay.sprinkling_money.domain.distribution;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DistributionTest {

    private User user;
    private Room room;
    private DistributionInfo distributionInfo;

    private OffsetDateTime offsetDateTime;

    @BeforeEach
    public void init() {
        user = new User("test");
        user.setId(0);

        room = mock(Room.class);
        distributionInfo = mock(DistributionInfo.class);
    }

    @Test
    public void validateReceivableTest() {
        Distribution distribution;
        DistributionState state;

        given:
        {
            doReturn(true).when(room).isJoined(any(User.class));
            doReturn(false).when(distributionInfo).isReceived();
            doReturn(false).when(distributionInfo).isReceived(any(User.class));

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
            distribution.setDistributionInfoList(List.of(distributionInfo));
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.NORMAL);
        }
    }

    @Test
    public void validateReceivableNotTargetTest() {
        Distribution distribution;
        DistributionState state;

        given:
        {
            distribution = new Distribution();
            distribution.setUser(user);
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(0);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.NOT_TARGET);
        }
    }


    @Test
    public void validateReceivableExpiredTest() {
        Distribution distribution;
        DistributionState state;

        OffsetDateTime executionTime = OffsetDateTime.now().minusMinutes(10).minusSeconds(1);

        given:
        {
            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setExecutionTime(executionTime);
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.EXPIRED);
        }
    }

    @Test
    public void validateReceivableNotTargetTest2() {
        Distribution distribution;
        DistributionState state;

        given:
        {
            doReturn(false).when(room).isJoined(any(User.class));

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.NOT_TARGET);
        }
    }


    @Test
    public void validateReceivableExhaustedTest() {
        Distribution distribution;
        DistributionState state;

        given:
        {
            doReturn(true).when(room).isJoined(any(User.class));
            doReturn(true).when(distributionInfo).isReceived();

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
            distribution.setDistributionInfoList(List.of(distributionInfo));
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.EXHAUSTED);
        }
    }


    @Test
    public void validateReceivableAlreadyReceivedTest() {
        Distribution distribution;
        DistributionState state;

        given:
        {
            doReturn(true).when(room).isJoined(any(User.class));
            doReturn(false).when(distributionInfo).isReceived();
            doReturn(true).when(distributionInfo).isReceived(any(User.class));

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
            distribution.setDistributionInfoList(List.of(distributionInfo));
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            state = distribution.validateReceivable(userInput);
        }

        then:
        {
            assertThat(state).isNotNull().isEqualTo(DistributionState.ALREADY_RECEIVED);
        }
    }

    @Test
    public void distributeTest() {
        Distribution distribution;
        DistributionState state;

        Money money;

        given:
        {

            doReturn(true).when(room).isJoined(any(User.class));
            doReturn(false).when(distributionInfo).isReceived();
            doReturn(false).when(distributionInfo).isReceived(any(User.class));
            doReturn(new Money(10L)).when(distributionInfo).allocate(any(User.class));

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
            distribution.setDistributionInfoList(List.of(distributionInfo));
        }

        when:
        {
            User userInput = new User("test");
            userInput.setId(1);

            money = distribution.distribute(userInput);
        }

        then:
        {
            assertThat(money).isNotNull().extracting("amount").isEqualTo(10L);
        }
    }

    @Test
    public void distributeFailureTest() {
        Distribution distribution;
        DistributionState state;

        Money money;

        given:
        {
            doReturn(true).when(room).isJoined(any(User.class));
            doReturn(false).when(distributionInfo).isReceived();
            doReturn(true).when(distributionInfo).isReceived(any(User.class));

            distribution = new Distribution();
            distribution.setUser(user);
            distribution.setRoom(room);
            distribution.setExecutionTime(OffsetDateTime.now());
            distribution.setDistributionInfoList(List.of(distributionInfo));
        }

        then:
        {
            User userInput = new User("test");
            userInput.setId(1);

            assertThrows(DistributionException.class, () -> distribution.distribute(userInput));
        }
    }

}
