package com.culturer.guishi_shoper.bean;

/**
 * Created by Administrator on 2018/7/27 0027.
 */

public class StoreBean
{
	/**
	 * Id : 3
	 * IsOffice : false
	 * Name : 零食店
	 * Icon :
	 * Tel :
	 * Desc : 零食
	 * Content : 零食
	 * Tab : 零食,标签二,标签三
	 * Address : 毛坦港湾
	 * Position : { latitude:,longitude:}
	 * UserId : 0
	 * IsClose : false
	 * CloseMsg : 9:00～19:00
	 * OpenTime :
	 * IsLock : false
	 * AddTime : 1532688520
	 * StoreTypeId : 0
	 * CommunityId : 0
	 */
	
	private int Id;
	private boolean IsOffice;
	private String Name;
	private String Icon;
	private String Tel;
	private String Desc;
	private String Content;
	private String Tab;
	private String Address;
	private String Position;
	private int UserId;
	private boolean IsClose;
	private String CloseMsg;
	private String OpenTime;
	private boolean IsLock;
	private int AddTime;
	private int StoreTypeId;
	private int CommunityId;
	
	public int getId() {
		return Id;
	}
	
	public void setId(int Id) {
		this.Id = Id;
	}
	
	public boolean isIsOffice() {
		return IsOffice;
	}
	
	public void setIsOffice(boolean IsOffice) {
		this.IsOffice = IsOffice;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public String getIcon() {
		return Icon;
	}
	
	public void setIcon(String Icon) {
		this.Icon = Icon;
	}
	
	public String getTel() {
		return Tel;
	}
	
	public void setTel(String Tel) {
		this.Tel = Tel;
	}
	
	public String getDesc() {
		return Desc;
	}
	
	public void setDesc(String Desc) {
		this.Desc = Desc;
	}
	
	public String getContent() {
		return Content;
	}
	
	public void setContent(String Content) {
		this.Content = Content;
	}
	
	public String getTab() {
		return Tab;
	}
	
	public void setTab(String Tab) {
		this.Tab = Tab;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String Address) {
		this.Address = Address;
	}
	
	public String getPosition() {
		return Position;
	}
	
	public void setPosition(String Position) {
		this.Position = Position;
	}
	
	public int getUserId() {
		return UserId;
	}
	
	public void setUserId(int UserId) {
		this.UserId = UserId;
	}
	
	public boolean isIsClose() {
		return IsClose;
	}
	
	public void setIsClose(boolean IsClose) {
		this.IsClose = IsClose;
	}
	
	public String getCloseMsg() {
		return CloseMsg;
	}
	
	public void setCloseMsg(String CloseMsg) {
		this.CloseMsg = CloseMsg;
	}
	
	public String getOpenTime() {
		return OpenTime;
	}
	
	public void setOpenTime(String OpenTime) {
		this.OpenTime = OpenTime;
	}
	
	public boolean isIsLock() {
		return IsLock;
	}
	
	public void setIsLock(boolean IsLock) {
		this.IsLock = IsLock;
	}
	
	public int getAddTime() {
		return AddTime;
	}
	
	public void setAddTime(int AddTime) {
		this.AddTime = AddTime;
	}
	
	public int getStoreTypeId() {
		return StoreTypeId;
	}
	
	public void setStoreTypeId(int StoreTypeId) {
		this.StoreTypeId = StoreTypeId;
	}
	
	public int getCommunityId() {
		return CommunityId;
	}
	
	public void setCommunityId(int CommunityId) {
		this.CommunityId = CommunityId;
	}
}
