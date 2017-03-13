#!/usr/bin/env python3

from __future__ import division
from pylab import *
from numpy import *
from scipy import *
from pprint import pprint as pp
from scipy.signal import fftconvolve
import os
import scipy.io.wavfile

file_paths = []
for folder, subs, files in os.walk('./train_safe'):
    for filename in files:
        file_paths.append(os.path.abspath(os.path.join(folder, filename)))


def find_frequency(sig, w):
    # Calculate autocorrelation (same thing as convolution, but with
    # one input reversed in time), and throw away the negative lags
    corr = fftconvolve(sig, sig[::-1], mode='full')
    corr = corr[len(corr)//2:]

    # Find the first low point
    # d = diff(corr)
    start = find(corr <= 0)[0]

    # Find the next peak after the low point (other than 0 lag).  This bit is
    # not reliable for long signals, due to the desired peak occurring between
    # samples, and other peaks appearing higher.
    # Should use a weighting function to de-emphasize the peaks at longer lags.
    peak = argmax(corr[start:])
    
    return w / (peak + start)

results = []
for f in file_paths:
    try:        
        w, signal = scipy.io.wavfile.read(f)

        gender = 'M' if '_M' in f else 'K'
        freq = find_frequency(signal, w)

        results.append((gender, freq))
    except:
        print(f + " file is broken.")
        
size = len(results)
print('size is: ' + str(size))

thresholds = []
for threshold in np.arange(0, 700):
    successes = []
    for (gender, freq) in results:
        g = 'K' if freq > threshold else 'M'

        if gender == g:
            successes.append(True)
        else:
            successes.append(False)

    success_rate = successes.count(True) / size
    thresholds.append(success_rate)

    print('[' + str(threshold) + ']: ' + str(success_rate))

best = argmax(thresholds)
print('Best is: ' + str(best) + ' with value of: ' + str(thresholds[best]))
