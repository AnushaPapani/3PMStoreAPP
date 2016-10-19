package com.three.pmstore.models;

import java.util.ArrayList;

/**
 * Created by shankar on 10/8/2016.
 */

public class Movie {
    private  String P_Name;
    private  String P_Qty;
    private  String P_Cost;
    private ArrayList<String> Attribute_Type;
    private ArrayList<String> Attribute_Value;
    private String Id;
//    private  String Attribute_Type;
//    private  String Attribute_Value;
    private  String Shipping_Carrier;
    private  String Cart_Prod_ID;
    private  String Cart_ID;
    private  String U_ID;
    private  String P_ID;
    private  String P_Customattribute;
    private  String Order_Date;
    private  String Status;
    private  String Payment_Type;
    private String TrackingId,
    PMCashUsed,
    Discount,
    TotalCost,
    COD_Charges,
    P_Image,Total,
    GrandTotal,
    Trackurl,
    Cancel_OrderId,
    RefundReferenceID,
    Cancel_Issue,
    Cancel_Comments,
    Cancel_Date,
    CancelStatus,
    Cancel_Issubmit,
    Order_Held,
    Preparation_in_progressDATE,
    PackedReadyDATE,
    CanceledDATE,
    DispatchedDATE,
    DeliveredDATE,
    Pre_dispatch_CancellationDATE,
    Post_dispatch_CancellationDATE,
    DonothingDATE,
    Refund_Status,
    Return_RefundType,
    Refund_IsSubmit,
    Refund_R_ID,
    Refund_PaymentType,
    ReturnRequestReceivedDATE,
    ReturnprocessinitiatedDATE,
    Reversepick_updoneDATE,
    RefunddoneSuccessfullyDATE,
    DoNothingDATE,
    Exchange_Status,
    Return_ExchangeType,
    Exchange_IsSubmit,
    Exchange_R_ID,
    Exchange_PaymentType,
    Return_Request_ReceivedDATE,
    Return_process_initiatedDATE,
    Reverse_pick_up_doneDATE,
    Next_product_dispatchedDATE,
    Exchange_done_SuccessfullyDATE,
    Do_NothingDATE,
    Review_Issubmit,
    Callmeback_IsSubmit,
    bmobile,
    bemail,
    CustomerName,
    currentdate,
    trackenabledate,
    returndisabledate;

    String username;
    String bline;
    String bcity;
    String bstate;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBpincode() {
        return bpincode;
    }

    public void setBpincode(String bpincode) {
        this.bpincode = bpincode;
    }

    public String getBstate() {
        return bstate;
    }

    public void setBstate(String bstate) {
        this.bstate = bstate;
    }

    public String getBcity() {
        return bcity;
    }

    public void setBcity(String bcity) {
        this.bcity = bcity;
    }

    public String getBline() {
        return bline;
    }

    public void setBline(String bline) {
        this.bline = bline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String bpincode;


    public String getTrackingId() {
        return TrackingId;
    }

    public void setTrackingId(String trackingId) {
        TrackingId = trackingId;
    }

    public String getPMCashUsed() {
        return PMCashUsed;
    }

    public void setPMCashUsed(String PMCashUsed) {
        this.PMCashUsed = PMCashUsed;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(String totalCost) {
        TotalCost = totalCost;
    }

    public String getCOD_Charges() {
        return COD_Charges;
    }

    public void setCOD_Charges(String COD_Charges) {
        this.COD_Charges = COD_Charges;
    }

    public String getP_Image() {
        return P_Image;
    }

    public void setP_Image(String p_Image) {
        P_Image = p_Image;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getTrackurl() {
        return Trackurl;
    }

    public void setTrackurl(String trackurl) {
        Trackurl = trackurl;
    }

    public String getCancel_OrderId() {
        return Cancel_OrderId;
    }

    public void setCancel_OrderId(String cancel_OrderId) {
        Cancel_OrderId = cancel_OrderId;
    }

    public String getRefundReferenceID() {
        return RefundReferenceID;
    }

    public void setRefundReferenceID(String refundReferenceID) {
        RefundReferenceID = refundReferenceID;
    }

    public String getCancel_Issue() {
        return Cancel_Issue;
    }

    public void setCancel_Issue(String cancel_Issue) {
        Cancel_Issue = cancel_Issue;
    }

    public String getCancel_Comments() {
        return Cancel_Comments;
    }

    public void setCancel_Comments(String cancel_Comments) {
        Cancel_Comments = cancel_Comments;
    }

    public String getCancel_Date() {
        return Cancel_Date;
    }

    public void setCancel_Date(String cancel_Date) {
        Cancel_Date = cancel_Date;
    }

    public String getCancelStatus() {
        return CancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        CancelStatus = cancelStatus;
    }

    public String getCancel_Issubmit() {
        return Cancel_Issubmit;
    }

    public void setCancel_Issubmit(String cancel_Issubmit) {
        Cancel_Issubmit = cancel_Issubmit;
    }

    public String getOrder_Held() {
        return Order_Held;
    }

    public void setOrder_Held(String order_Held) {
        Order_Held = order_Held;
    }

    public String getPreparation_in_progressDATE() {
        return Preparation_in_progressDATE;
    }

    public void setPreparation_in_progressDATE(String preparation_in_progressDATE) {
        Preparation_in_progressDATE = preparation_in_progressDATE;
    }

    public String getPackedReadyDATE() {
        return PackedReadyDATE;
    }

    public void setPackedReadyDATE(String packedReadyDATE) {
        PackedReadyDATE = packedReadyDATE;
    }

    public String getCanceledDATE() {
        return CanceledDATE;
    }

    public void setCanceledDATE(String canceledDATE) {
        CanceledDATE = canceledDATE;
    }

    public String getDispatchedDATE() {
        return DispatchedDATE;
    }

    public void setDispatchedDATE(String dispatchedDATE) {
        DispatchedDATE = dispatchedDATE;
    }

    public String getDeliveredDATE() {
        return DeliveredDATE;
    }

    public void setDeliveredDATE(String deliveredDATE) {
        DeliveredDATE = deliveredDATE;
    }

    public String getPre_dispatch_CancellationDATE() {
        return Pre_dispatch_CancellationDATE;
    }

    public void setPre_dispatch_CancellationDATE(String pre_dispatch_CancellationDATE) {
        Pre_dispatch_CancellationDATE = pre_dispatch_CancellationDATE;
    }

    public String getPost_dispatch_CancellationDATE() {
        return Post_dispatch_CancellationDATE;
    }

    public void setPost_dispatch_CancellationDATE(String post_dispatch_CancellationDATE) {
        Post_dispatch_CancellationDATE = post_dispatch_CancellationDATE;
    }

    public String getDonothingDATE() {
        return DonothingDATE;
    }

    public void setDonothingDATE(String donothingDATE) {
        DonothingDATE = donothingDATE;
    }

    public String getRefund_Status() {
        return Refund_Status;
    }

    public void setRefund_Status(String refund_Status) {
        Refund_Status = refund_Status;
    }

    public String getReturn_RefundType() {
        return Return_RefundType;
    }

    public void setReturn_RefundType(String return_RefundType) {
        Return_RefundType = return_RefundType;
    }

    public String getRefund_IsSubmit() {
        return Refund_IsSubmit;
    }

    public void setRefund_IsSubmit(String refund_IsSubmit) {
        Refund_IsSubmit = refund_IsSubmit;
    }

    public String getRefund_R_ID() {
        return Refund_R_ID;
    }

    public void setRefund_R_ID(String refund_R_ID) {
        Refund_R_ID = refund_R_ID;
    }

    public String getRefund_PaymentType() {
        return Refund_PaymentType;
    }

    public void setRefund_PaymentType(String refund_PaymentType) {
        Refund_PaymentType = refund_PaymentType;
    }

    public String getReturnRequestReceivedDATE() {
        return ReturnRequestReceivedDATE;
    }

    public void setReturnRequestReceivedDATE(String returnRequestReceivedDATE) {
        ReturnRequestReceivedDATE = returnRequestReceivedDATE;
    }

    public String getReturnprocessinitiatedDATE() {
        return ReturnprocessinitiatedDATE;
    }

    public void setReturnprocessinitiatedDATE(String returnprocessinitiatedDATE) {
        ReturnprocessinitiatedDATE = returnprocessinitiatedDATE;
    }

    public String getReversepick_updoneDATE() {
        return Reversepick_updoneDATE;
    }

    public void setReversepick_updoneDATE(String reversepick_updoneDATE) {
        Reversepick_updoneDATE = reversepick_updoneDATE;
    }

    public String getRefunddoneSuccessfullyDATE() {
        return RefunddoneSuccessfullyDATE;
    }

    public void setRefunddoneSuccessfullyDATE(String refunddoneSuccessfullyDATE) {
        RefunddoneSuccessfullyDATE = refunddoneSuccessfullyDATE;
    }

    public String getDoNothingDATE() {
        return DoNothingDATE;
    }

    public void setDoNothingDATE(String doNothingDATE) {
        DoNothingDATE = doNothingDATE;
    }

    public String getExchange_Status() {
        return Exchange_Status;
    }

    public void setExchange_Status(String exchange_Status) {
        Exchange_Status = exchange_Status;
    }

    public String getReturn_ExchangeType() {
        return Return_ExchangeType;
    }

    public void setReturn_ExchangeType(String return_ExchangeType) {
        Return_ExchangeType = return_ExchangeType;
    }

    public String getExchange_IsSubmit() {
        return Exchange_IsSubmit;
    }

    public void setExchange_IsSubmit(String exchange_IsSubmit) {
        Exchange_IsSubmit = exchange_IsSubmit;
    }

    public String getExchange_R_ID() {
        return Exchange_R_ID;
    }

    public void setExchange_R_ID(String exchange_R_ID) {
        Exchange_R_ID = exchange_R_ID;
    }

    public String getExchange_PaymentType() {
        return Exchange_PaymentType;
    }

    public void setExchange_PaymentType(String exchange_PaymentType) {
        Exchange_PaymentType = exchange_PaymentType;
    }

    public String getReturn_Request_ReceivedDATE() {
        return Return_Request_ReceivedDATE;
    }

    public void setReturn_Request_ReceivedDATE(String return_Request_ReceivedDATE) {
        Return_Request_ReceivedDATE = return_Request_ReceivedDATE;
    }

    public String getReturn_process_initiatedDATE() {
        return Return_process_initiatedDATE;
    }

    public void setReturn_process_initiatedDATE(String return_process_initiatedDATE) {
        Return_process_initiatedDATE = return_process_initiatedDATE;
    }

    public String getReverse_pick_up_doneDATE() {
        return Reverse_pick_up_doneDATE;
    }

    public void setReverse_pick_up_doneDATE(String reverse_pick_up_doneDATE) {
        Reverse_pick_up_doneDATE = reverse_pick_up_doneDATE;
    }

    public String getNext_product_dispatchedDATE() {
        return Next_product_dispatchedDATE;
    }

    public void setNext_product_dispatchedDATE(String next_product_dispatchedDATE) {
        Next_product_dispatchedDATE = next_product_dispatchedDATE;
    }

    public String getExchange_done_SuccessfullyDATE() {
        return Exchange_done_SuccessfullyDATE;
    }

    public void setExchange_done_SuccessfullyDATE(String exchange_done_SuccessfullyDATE) {
        Exchange_done_SuccessfullyDATE = exchange_done_SuccessfullyDATE;
    }

    public String getDo_NothingDATE() {
        return Do_NothingDATE;
    }

    public void setDo_NothingDATE(String do_NothingDATE) {
        Do_NothingDATE = do_NothingDATE;
    }

    public String getReview_Issubmit() {
        return Review_Issubmit;
    }

    public void setReview_Issubmit(String review_Issubmit) {
        Review_Issubmit = review_Issubmit;
    }

    public String getCallmeback_IsSubmit() {
        return Callmeback_IsSubmit;
    }

    public void setCallmeback_IsSubmit(String callmeback_IsSubmit) {
        Callmeback_IsSubmit = callmeback_IsSubmit;
    }

    public String getBmobile() {
        return bmobile;
    }

    public void setBmobile(String bmobile) {
        this.bmobile = bmobile;
    }

    public String getBemail() {
        return bemail;
    }

    public void setBemail(String bemail) {
        this.bemail = bemail;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getTrackenabledate() {
        return trackenabledate;
    }

    public void setTrackenabledate(String trackenabledate) {
        this.trackenabledate = trackenabledate;
    }

    public String getReturndisabledate() {
        return returndisabledate;
    }

    public void setReturndisabledate(String returndisabledate) {
        this.returndisabledate = returndisabledate;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public String getP_Qty() {
        return P_Qty;
    }

    public void setP_Qty(String p_Qty) {
        P_Qty = p_Qty;
    }

    public String getP_Cost() {
        return P_Cost;
    }

    public void setP_Cost(String p_Cost) {
        P_Cost = p_Cost;
    }

    public ArrayList<String> getAttribute_Type() {
        return Attribute_Type;
    }

    public void setAttribute_Type(ArrayList<String> attribute_Type) {
        Attribute_Type = attribute_Type;
    }

    public ArrayList<String> getAttribute_Value() {
        return Attribute_Value;
    }

    public void setAttribute_Value(ArrayList<String> attribute_Value) {
        Attribute_Value = attribute_Value;
    }

    public String getShipping_Carrier() {
        return Shipping_Carrier;
    }

    public void setShipping_Carrier(String shipping_Carrier) {
        Shipping_Carrier = shipping_Carrier;
    }

    public String getCart_Prod_ID() {
        return Cart_Prod_ID;
    }

    public void setCart_Prod_ID(String cart_Prod_ID) {
        Cart_Prod_ID = cart_Prod_ID;
    }

    public String getCart_ID() {
        return Cart_ID;
    }

    public void setCart_ID(String cart_ID) {
        Cart_ID = cart_ID;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_Customattribute() {
        return P_Customattribute;
    }

    public void setP_Customattribute(String p_Customattribute) {
        P_Customattribute = p_Customattribute;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPayment_Type() {
        return Payment_Type;
    }

    public void setPayment_Type(String payment_Type) {
        Payment_Type = payment_Type;
    }
}
