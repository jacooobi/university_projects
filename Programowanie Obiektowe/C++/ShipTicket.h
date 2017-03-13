//
// Created by Jacek Kubiak on 21/01/16.
//

#ifndef PROJEKTCPP_SHIPTICKET_H
#define PROJEKTCPP_SHIPTICKET_H

#include "BaseTicket.h"

class ShipTicket : public BaseTicket {
public:
    ShipTicket(bool freeTicket, string start, string finish);
    string getTicketCategory();
private:
    bool carTransport;
};


#endif //PROJEKTCPP_SHIPTICKET_H
