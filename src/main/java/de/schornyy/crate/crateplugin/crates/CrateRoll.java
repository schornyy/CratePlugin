package de.schornyy.crate.crateplugin.crates;

import de.schornyy.crate.crateplugin.CratePlugin;
import de.schornyy.crate.crateplugin.player.CratePlayer;
import de.schornyy.crate.crateplugin.player.CratePlayerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import sun.security.util.ArrayUtil;

import java.util.Random;

public class CrateRoll {

    private Player roller;
    private Crate crate;
    private Inventory inventory;
    private int[] rollingCounts = {0,1,2,3,4,5,6,7,8,9,10,13,16,19,22,25,28,31,34,37,40,45,50,55,60,55,70};
    private int taskID = (int) (Math.random() * 9999), count = 0;
    private CrateRollStates rollStates;

    public CrateRoll(Player player, Crate crate) {
        this.roller = player;
        this.crate = crate;
        this.inventory = crate.getInventory();
    }

    public void roll() {
        getRoller().openInventory(this.inventory);
        this.rollStates = CrateRollStates.ROLLING;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CratePlugin.plugin, new Runnable() {
            @Override
            public void run() {
                count++;
                if(ArrayUtils.contains(rollingCounts, count)) {
                    for(int slots : crate.getRollingSlots()) {
                        getInventory().setItem(slots, CrateUtils.getRandomItemFromCrate(getCrate()));
                    }
                } else if (count >= rollingCounts[rollingCounts.length - 1]) {
                    Bukkit.getScheduler().cancelTask(getTaskID());
                    setRollStates(CrateRollStates.END);
                    CratePlayer cratePlayer = CratePlayerUtils.getCratePlayerByPlayer(getRoller());
                    cratePlayer.setOpening(false);
                    cratePlayer.setCrateRoll_opening(null);
                }
            }
        }, 1L,1L);
    }

    public CrateRollStates getRollStates() {
        return rollStates;
    }

    public void setRollStates(CrateRollStates rollStates) {
        this.rollStates = rollStates;
    }

    public Player getRoller() {
        return roller;
    }

    public int getTaskID() {
        return taskID;
    }

    public Crate getCrate() {
        return crate;
    }

    public int[] getRollingCounts() {
        return rollingCounts;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
