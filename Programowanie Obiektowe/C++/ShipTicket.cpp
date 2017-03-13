//
// Created by Jacek Kubiak on 21/01/16.
//

#include "ShipTicket.h"

ShipTicket::ShipTicket(bool carTransport, string start, string finish) : BaseTicket(start, finish) {
    this->carTransport = carTransport;
}

string ShipTicket::getTicketCategory() {
    return "Rejs statkiem";
}