//
// Created by Jacek Kubiak on 21/01/16.
//

#ifndef PROJEKTCPP_JOINEDTICKET_H
#define PROJEKTCPP_JOINEDTICKET_H


#include <vector>
#include "BaseTicket.h"

class JoinedTicket : public BaseTicket {
private:
    vector<BaseTicket*> tickets;
public:
    JoinedTicket(vector<BaseTicket*> tickets);
    string getTicketCategory();
    string display();
};

#endif //PROJEKTCPP_JOINEDTICKET_H
