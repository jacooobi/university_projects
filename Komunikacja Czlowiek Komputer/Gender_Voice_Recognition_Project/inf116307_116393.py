#!/usr/bin/env python3

from __future__ import division
from pylab import *
from numpy import *
from scipy import *
from pprint import pprint as pp
from scipy.signal import fftconvolve
import sys
import os
import scipy.io.wavfile

if len(sys.argv) < 2:
    exit(1)

THRESHOLD = 90
folder = os.getcwd()
filename = os.path.abspath(os.path.join(folder, sys.argv[1]))

if not os.path.isfile(filename):
    exit(2)

def find_frequency(sig, w):
    # Autokorelacja
    corr = fftconvolve(sig, sig[::-1], mode='full')
    corr = corr[len(corr)//2:]

    # Pierwszy punkt poniżej zera
    start = find(corr <= 0)[0]

    # Największy pik
    peak = argmax(corr[start:])

    # Częstotliwość
    return w / (peak + start)

w, signal = scipy.io.wavfile.read(filename)
freq = find_frequency(signal, w)

gender = 'K' if freq > THRESHOLD else 'M'

print(gender)
