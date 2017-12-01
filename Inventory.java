package main;
public class Inventory {
    
    private Item[] inv = new Item[9];
    
    private Item[] hands = new Item[2];
    
    public Inventory() {
        for (int x = 0; x < inv.length; x++) {
            setInv(x, new DefaultItem());
        }
        
        for (int x = 0; x < hands.length; x++) {
            setEquip(x, new DefaultItem());
        }
    }
    
    public void setInv(int pos, Item item) {
        inv[pos] = item;
    }
    
    public void setEquip(int pos, Item item) {
        hands[pos] = item;
    }
    
    public String printInv() {
        String res = "";
        
        res += "\nInventory: \n";
        for (int x = 0; x < 9; x++) {
            if (!(getInvItem(x) instanceof DefaultItem)) {
                 res += " " + (x + 1) + ". " + getInvItem(x).getItemName() + " (" + getInvItem(x).getWorth() + ") x" + getInvItem(x).getNumInInv() + "\n";
            }
            else {
                res += " " + (x + 1) + ".\n";
            }
            //area.append("Run Out\n");
        }
        
        res += "\nEquiped: \n";
        
        for (int x = 0; x < 2; x++) {
            if (!(getEquipItem(x) instanceof DefaultItem)) {
                res += " " + (x + 1) + ". " + getEquipItem(x).getItemName() + " (" + getEquipItem(x).getWorth() + ")\n";
            }
            else {
                res += " " + (x + 1) + ".\n";
            }
        }
        
        return res;
    }
    
    public Item getEquipItem(int pos) {
        return hands[pos];
    }
    
    public Item getInvItem(int pos) {
        return inv[pos];
    }
}