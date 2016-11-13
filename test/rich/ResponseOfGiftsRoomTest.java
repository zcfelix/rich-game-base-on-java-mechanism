package rich;

import org.junit.Before;
import org.junit.Test;
import rich.command.Command;
import rich.command.RollCommand;
import rich.place.GiftsRoom;
import rich.place.Place;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponseOfGiftsRoomTest {

    private GameMap map;
    private Dice dice;

    private Place giftsRoom;
    private Place starting;

    private Player player;
    private Command roll;

    @Before
    public void before() {
        map = mock(GameMap.class);
        dice = mock(Dice.class);
        starting = mock(Place.class);
        giftsRoom = new GiftsRoom();

        roll = new RollCommand(map, dice);
        when(dice.next()).thenReturn(1);
        when(map.move(eq(starting), eq(1))).thenReturn(giftsRoom);

        player = Player.createPlayerWithStarting(starting);
        player.execute(roll);
        assertThat(player.getState(), is(Player.State.WAITING_FOR_RESPONSE));
    }

    @Test
    public void should_exit_gifts_room_when_player_input_is_wrong() {
        player.respond(RollCommand.ExitGiftsRoom);
        assertThat(player.getState(), is(Player.State.END_TURN));
    }

    @Test
    public void should_choose_money_bonus() {
        player.respond(RollCommand.ChooseBonus);
        assertThat(player.getState(), is(Player.State.END_TURN));
        assertThat(player.getBalance(), is(GameConstant.MONEY_BONUS));
    }

    @Test
    public void should_choose_point_bonus() {
        player.respond(RollCommand.ChoosePoint);
        assertThat(player.getState(), is(Player.State.END_TURN));
        assertThat(player.getPoints(), is(GameConstant.POINT_BONUS));
    }

    @Test
    public void should_choose_mascot_bonus() {
        player.respond(RollCommand.ChooseMascot);
        assertThat(player.getState(), is(Player.State.END_TURN));
        assertThat(player.getNoPunishTimes(), is(GameConstant.MASCOT_BONUS));
    }

}
