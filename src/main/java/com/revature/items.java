package com.revature;

public class items {
    private int itemId;
    private int itemName;

    public int getItemName() {
        return itemName;
    }

    public void setItemName(int itemName) {
        this.itemName = itemName;
    }

    private int price;
    private int stock;

    public int getItemId() {
        return itemId;
    }

    public void itemInfo(){
        System.out.println("item name: " + itemName + "itemId: " + itemId + "price: " + price + "stock: " + stock);
    }



    public items(int itemId, int itemName, int price, int stock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
