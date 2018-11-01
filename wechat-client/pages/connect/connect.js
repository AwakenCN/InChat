// pages/connect/connect.js
var app = getApp();
var MQTT = require("../../utils/paho-mqtt.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    server_addr: '',
    user_name: null,
    user_psw: null,
    error_message: '',
    switch_checked:false,
    btn_loading:false
  },

  server_addr_input:function(e){
    console.log(e);
    this.setData({
      server_addr:e.detail.value
    });
  },
  user_name_input: function (e) {
    console.log(e);
    this.setData({
      user_name: e.detail.value
    });
  },
  user_psw_input: function (e) {
    console.log(e);
    this.setData({
      user_psw: e.detail.value
    });
  },
  switch_change:function(e){
    console.log(e);
    this.setData({
      switch_checked: e.detail.value
    });
  },
  
  btn_connect:function(){
    //查看输入是否为空，设置错误信息
    if(this.data.server_addr==''||this.data.server_addr==null){
      this.setData({
        error_message:"server address can not be empty!"
      });
      return;
    }
    if(this.data.switch_checked){
      if (this.data.user_name == null || this.data.user_name == '') {
        this.setData({
          error_message: "user name can not be empty!"
        });
        return;
      }
      if (this.data.user_psw == null || this.data.user_psw == '') {
        this.setData({
          error_message: "user password can not be empty!"
        });
        return;
      }
    }
    
    //reset error message
    if(this.data.error_message){
      this.setData({
        error_message:''
      });
    }
    //在按钮上显示加载标志
    this.setData({
      btn_loading:true
    });

    var client = new MQTT.Client("wss://" + this.data.server_addr+"/mqtt", "clientId_" + Math.random().toString(36).substr(2));
    var that = this;
    //connect to  MQTT broker
    var connectOptions = {
      timeout: 10,
      useSSL: true,
      cleanSession: true,
      keepAliveInterval: 30,
      reconnect: true,
      onSuccess: function () {
        console.log('connected');

        app.globalData.mqtt_client = client;

        client.onMessageArrived = function (msg) {
          /*
          if (typeof app.globalData.onMessageArrived === 'function') {
            return app.globalData.onMessageArrived(msg);
          }*/
          if(app.globalData.messages==null){
            app.globalData.messages = [{ topic: msg.topic, message: msg.payloadString }];
          }else{
            app.globalData.messages =
              [{ topic: msg.topic, message: msg.payloadString }].concat(app.globalData.messages);
          }
          
        }

        client.onConnectionLost = function (responseObject) {
          if (typeof app.globalData.onConnectionLost === 'function') {
            return app.globalData.onConnectionLost(responseObject);
          }
          if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
          }
        }
        //去除按钮上的加载标志
        that.setData({
          btn_loading: false
        });

        wx.switchTab({
          url: '../subscribe/subscribe',
        });
      },
      onFailure: function (option) {
        console.log(option);
        //去除按钮上的加载标志
        that.setData({
          btn_loading: false
        });
        wx.showModal({
          //title: msg.destinationName,
          content: option.errorMessage
        });
      }
    };
    if(this.data.switch_checked){
      connectOptions.userName = this.data.user_name;
      connectOptions.password = this.data.user_psw;
    }

    client.connect(connectOptions);

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
    /*if (app.globalData.mqtt_client != null) {
      wx.reLaunch({
        url: '../subscribe/subscribe',
      });
      return;
    }*/
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