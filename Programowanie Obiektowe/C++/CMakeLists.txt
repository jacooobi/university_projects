cmake_minimum_required(VERSION 3.3)
project(ProjektCPP)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp BaseTicket.cpp BaseTicket.h ShipTicket.cpp ShipTicket.h PlaneTicket.cpp PlaneTicket.h JoinedTicket.cpp JoinedTicket.h ReservationBase.h BookingAssistant.cpp BookingAssistant.h Booking.cpp Booking.h)
add_executable(ProjektCPP ${SOURCE_FILES})