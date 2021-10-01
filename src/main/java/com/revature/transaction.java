package com.revature;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class transaction {

    /*
Put order to make purchase
Input: Product ID, Seller ID, Quantity, Payment Method(Visa or MasterCard), VIP points used
Output: Insert 1 tuple for each Product ID into table PutOrder under the same Order ID:
        Order IDs set to the latest created Order ID + 1.
        Status set to “In Progress”.
        Quantity of Products in the table Has to be updated accordingly.
        Date placed set to Today.
        Shipping Date set to Tomorrow.
        Arrival Date set to 5 days from today.
        VIP points used deducted from the Customer’s VIP points
Return: Total cost = cost of all products in the order - VIP points/50
*/
//    public double putOrder(String customer_ID, String product_ID, String seller_ID, String Quantity,
//                           String Payment_method, String VipPoints_used, Connection con) throws SQLException {
//        int product_id = Integer.parseInt(product_ID);
//        int seller_id = Integer.parseInt(seller_ID);
//        int customer_id = Integer.parseInt(customer_ID);
//        int quantity = Integer.parseInt(Quantity);
//        int vip_points_used = Integer.parseInt(VipPoints_used);
//        double totalCost = 0;
//
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        // the date of TODAY which is when order placed
//        Calendar c = Calendar.getInstance();
//        String today = formatter.format(c.getTime());
//
//        // the shipping date
//        c.add(Calendar.DATE, 1);
//        String today_plus1 = formatter.format(c.getTime());
//
//        // the expected delivery date
//        c.add(Calendar.DATE, 4);
//        String today_plus5 = formatter.format(c.getTime());
//
//        // get the current vip points the customer has
//        int current_vip_points = 0;
//        try (PreparedStatement ps = con.prepareStatement
//                ("SELECT VIP_Points FROM VIP_1 WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHERE Customer_id = ?)")) {
//            ps.setInt(1, customer_id);
//            ResultSet temp = ps.executeQuery();
//            while (temp.next()) {
//                current_vip_points = temp.getInt("VIP_Points");
//            }
//        }
//        // when VIP points entered are larger than currently having, report error
//        if (current_vip_points < vip_points_used) return -1;
//
//        // Get the order number of the last order entered in the system
//        int lastOrderID = 0;
//        int orderID;
//
//        try (PreparedStatement ps = con.prepareStatement
//                ("SELECT MAX(Order_number) FROM PutOrder")) {
//            ResultSet temp = ps.executeQuery();
//            while (temp.next()) {
//                lastOrderID = temp.getInt("MAX(ORDER_NUMBER)");
//                System.out.println("test1\n");
//            }
//        }
//        orderID = lastOrderID + 1;
//
//        // Insert new order into PutOrder
//        try (PreparedStatement ps = con.prepareStatement
//                ("INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number, Product_ID, Customer_id, Seller_ID, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
//            ps.setString(1, "In Progress");
//            ps.setString(2, Payment_method);
//            ps.setString(3, today);
//            ps.setString(4, today_plus1);
//            ps.setString(5, today_plus5);
//            ps.setInt(6, vip_points_used);
//            ps.setInt(7, orderID);
//            ps.setInt(8, product_id);
//            ps.setInt(9, customer_id);
//            ps.setInt(10, seller_id);
//            ps.setInt(11, quantity);
//
//            ps.executeQuery();
//
//            System.out.println("test\n");
//
//            ps.close();
//        }
//
//        // Update VIP points by deducting vip points used
//        try (PreparedStatement ps = con.prepareStatement
//                ("UPDATE VIP_1 SET VIP_Points = VIP_Points - ?" +
//                        "WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHERE Customer_id = ?)")) {
//            ps.setInt(1, vip_points_used);
//            ps.setInt(2, customer_id);
//            ps.executeQuery();
//            ps.close();
//        }
//
//        // return the total cost of the order
//        try (PreparedStatement ps = con.prepareStatement
//                ("SELECT Price FROM Has WHERE Seller_ID = ? AND Product_ID = ?")) {
//            ps.setInt(1, seller_id);
//            ps.setInt(2, product_id);
//            ResultSet temp = ps.executeQuery();
//
//            while (temp.next()) {
//                totalCost = quantity * temp.getInt("Price") - vip_points_used/50;
//            }
//            ps.close();
//        }
//
//        return totalCost;
//    }

}
