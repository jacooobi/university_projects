//
// Created by Jacek Kubiak on 21/01/16.
//

#include "PlaneTicket.h"

PlaneTicket::PlaneTicket(string ticketType, string start, string finish) : BaseTicket(start, finish) {
    this->ticketType = ticketType;
}

string PlaneTicket::getTicketCategory() {
    return "Lot samolotem";
}