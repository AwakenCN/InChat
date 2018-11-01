// pages/publish/publish.js
var app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pub_topic:'',
    pub_message:'',
    qos_array:[0,1,2],
    retained_array:[false,true],
    qos_index:0,
    retained_index:0
  },
  pub_topic_input:function(e){
    console.log(e);
    this.setData({
      pub_topic:e.detail.value
    });
  },
  qos_picker_change: function (e) {
    console.log(e);
    this.setData({
      qos_index: e.detail.value
    });
  },
  retained_picker_change:function(e){
    console.log(e);
    this.setData({
      retained_index: e.detail.value
    });
  },
  message_input:function(e){
    console.log(e);
    this.setData({
      pub_message:e.detail.value
    });
  },
  btn_publish:function(){
    app.globalData.tst;
    if(this.data.pub_topic==''||this.data.pub_topic==null){
      wx.showToast({
        title: 'input topic',
        icon:'loading',
        duration:2000
      });
      return;
    }
    if(this.data.pub_message==''||this.data.pub_message==null){
      wx.showToast({
        title: 'input message',
        icon: 'loading',
        duration: 2000
      });
      return;
    }
    if (app.globalData.mqtt_client && app.globalData.mqtt_client.isConnected()){
      app.globalData.mqtt_client.publish(this.data.pub_topic,
        this.data.pub_message,
        this.data.qos_array[this.data.qos_index],
        this.data.retained_array[this.data.retained_index]
        );
        wx.showToast({
          title: 'publish success',
          icon:"success",
          duration:2000
        });
    }else{
      wx.showToast({
        title: 'client invalid',
        icon: "loading",
        duration: 2000
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