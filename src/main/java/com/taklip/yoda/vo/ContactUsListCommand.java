package com.taklip.yoda.vo;

public class ContactUsListCommand {
    String srContactUsName;
    String srActive;
    int srPageNo;
    ContactUsDisplayCommand contactUsCommands[];

    public ContactUsDisplayCommand getContactUs(int index) {
        return contactUsCommands[index];
    }

//	public void reset(ActionMapping mapping, HttpServletRequest request) {
//		String USER = "contactUs.*contactUsId";
//		int count = 0;
//		Enumeration enumeration = request.getParameterNames();
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			if (name.matches(USER)) {
//				count++;
//			}
//		}
//		contactUsCommands = new ContactUsDisplayCommand[count];
//		for (int i = 0; i < contactUsCommands.length; i++) {
//			contactUsCommands[i] = new ContactUsDisplayCommand();
//		}
//	}

    int pageCount;
    int startPage;
    int endPage;
    int pageNo;
    boolean empty;

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public String getSrActive() {
        return srActive;
    }

    public void setSrActive(String srActive) {
        this.srActive = srActive;
    }

    public int getSrPageNo() {
        return srPageNo;
    }

    public void setSrPageNo(int srPageNo) {
        this.srPageNo = srPageNo;
    }

    public String getSrContactUsName() {
        return srContactUsName;
    }

    public void setSrContactUsName(String srContactUsName) {
        this.srContactUsName = srContactUsName;
    }

    public ContactUsDisplayCommand[] getContactUsCommands() {
        return contactUsCommands;
    }

    public void setContactUsCommands(ContactUsDisplayCommand[] contactUsCommands) {
        this.contactUsCommands = contactUsCommands;
    }
}
