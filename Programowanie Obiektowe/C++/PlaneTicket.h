//
// Created by Jacek Kubiak on 21/01/16.
//

#ifndef PROJEKTCPP_PLANETICKET_H
#define PROJEKTCPP_PLANETICKET_H

#include "BaseTicket.h"

class PlaneTicket : public BaseTicket {
    string ticketType;
public:
    PlaneTicket(string ticketType, string start, string finish);
    string getTicketCategory();
};


#endif //PROJEKTCPP_PLANETICKET_H
