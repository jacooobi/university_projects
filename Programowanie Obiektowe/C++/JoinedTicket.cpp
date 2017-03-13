//
// Created by Jacek Kubiak on 21/01/16.
//

#include "JoinedTicket.h"

JoinedTicket::JoinedTicket(vector<BaseTicket*> tickets) : BaseTicket() {
    for(vector<BaseTicket*>::iterator it = tickets.begin(); it != tickets.end(); ++it) {
        this->tickets.push_back(*it);
    }

    this->setStartingLocation(this->tickets.at(0)->getStartingLocation());
    this->setDestinatedLocation(this->tickets.at(tickets.size()-1)->getDestinatedLocation());
}

string JoinedTicket::getTicketCategory() {
    return "Bilet łączony";
}

string JoinedTicket::display() {
    return (string(getTicketCategory()) + " " + getStartingLocation() +
            "-" + getDestinatedLocation() + "(" + to_string(this->tickets.size()) + " przystanki pośrednie)");
}