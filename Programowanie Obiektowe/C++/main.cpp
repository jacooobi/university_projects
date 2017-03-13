#include <iostream>
#include "BookingAssistant.h"


using namespace std;

int main() {
    BookingAssistant *assistant = new BookingAssistant("filename");
    assistant->call();

    return 0;
}