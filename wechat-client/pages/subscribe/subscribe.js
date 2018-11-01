// pages/subscribe/subscribe.js
var app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    current_tab:0,
    qos_array:[0,1,2],
    sub_topic:'',
    unsub_topic:'',
    index:0
  },
  switch_tab:function(e){
    console.log(e);
    this.setData({
      current_tab:e.target.dataset.current
    });
  },
  swiper_change:function(e){
    console.log(e);
    this.setData({
      current_tab:e.detail.current
    });
  },
  picker_change:function(e){
    console.log(e);
    this.setData({
      index:e.detail.value
    });
  },
  btn_subscribe:function(){
    if(this.data.sub_topic==''||this.data.sub_topic==null){
      wx.showToast({
        title: 'input topic',
        icon:'loading',
        duration:2000
      });
      return;
    }
    if (app.globalData.mqtt_client && app.globalData.mqtt_client.isConnected()){
      app.globalData.mqtt_client.subscribe(this.data.sub_topic,{
        qos:this.data.qos_array[this.data.index],
        onSuccess:function(){
          wx.showToast({
            title: 'success',
            icon: 'success',
            duration: 2000
          });
        },
        onFailure:function(){
          wx.showToast({
            title: 'Failure',
            icon:'loading',
            duration: 2000
          });
        },
      });
    }
    
  },
  sub_input:function(e){
    console.log(e);
    this.setData({
      sub_topic: e.detail.value      
    });
  },
  unsub_input:function(e){
    console.log(e);
    this.setData({
      unsub_topic:e.detail.value
    });
  },
  btn_unsubscribe:function(e){
    if (this.data.unsub_topic == '' || this.data.unsub_topic == null) {
      wx.showToast({
        title: 'input topic',
        icon: 'loading',
        duration: 2000
      });
      return;
    }
    if (app.globalData.mqtt_client && app.globalData.mqtt_client.isConnected()) {
      app.globalData.mqtt_client.unsubscribe(this.data.unsub_topic, {
        onSuccess: function () {
          wx.showToast({
            title: 'success',
            icon: 'success',
            duration: 2000
          });
        },
        onFailure: function () {
          wx.showToast({
            title: 'Failure',
            icon: 'loading',
            duration: 2000
          });
        },
      });
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})