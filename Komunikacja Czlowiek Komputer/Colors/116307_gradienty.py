#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import division             # Division in Python 2.7
import matplotlib
matplotlib.use('Agg')                       # So that we can render files without GUI
import matplotlib.pyplot as plt
from matplotlib import rc
import numpy as np
import math

from matplotlib import colors

def plot_color_gradients(gradients, names):
    # For pretty latex fonts (commented out, because it does not work on some machines)
    #rc('text', usetex=True)
    #rc('font', family='serif', serif=['Times'], size=10)
    rc('legend', fontsize=10)

    column_width_pt = 400         # Show in latex using \the\linewidth
    pt_per_inch = 72
    size = column_width_pt / pt_per_inch

    fig, axes = plt.subplots(nrows=len(gradients), sharex=True, figsize=(size, 0.75 * size))
    fig.subplots_adjust(top=1.00, bottom=0.05, left=0.25, right=0.95)


    for ax, gradient, name in zip(axes, gradients, names):
        # Create image with two lines and draw gradient on it
        img = np.zeros((2, 1024, 3))
        for i, v in enumerate(np.linspace(0, 1, 1024)):
            img[:, i] = gradient(v)

        im = ax.imshow(img, aspect='auto')
        im.set_extent([0, 1, 0, 1])
        ax.yaxis.set_visible(False)

        pos = list(ax.get_position().bounds)
        x_text = pos[0] - 0.25
        y_text = pos[1] + pos[3]/2.
        fig.text(x_text, y_text, name, va='center', ha='left', fontsize=10)

    fig.savefig('my-gradients.pdf')

def hsv2rgb(h, s, v):
    # 0 <= H <= 360
    # 0 <= S <= 1
    # 0 <= V <= 1
    if (s == 0):
      return (v, v, v)

    c = v*s
    x = c * (1 - math.fabs((h/60) % 2 - 1))
    m = v-c

    if (h >= 0 and h < 60):
      return (c, x, 0)
    elif (h >= 60 and h < 120):
      return (x, c, 0)
    elif (h >= 120 and h < 180):
      return (0, c, x)
    elif (h >= 180 and h < 240):
      return (0, x, c)
    elif (h >= 240 and h < 300):
      return (x, 0, c)
    elif (h >= 300 and h < 360):
      return (c, 0, x)

def gradient_rgb_bw(v):
    return (v, v, v)


def gradient_rgb_gbr(v):
    if v > 0.5015:
      return (v*2, 0, 1-v*2)
    elif v < 0.4985:
      return (0, 1-v*2, v*2)

    return (0, 0, 1)

def gradient_rgb_gbr_full(v):
    if (v < 0.25):
      return (0, 1, v*4)
    elif (v > 0.25 and v < 0.5):
      return (0, 1-(4*v-2), 1)
    elif (v > 0.5 and v < 0.75):
      return (4*v-2, 0, 1)

    return (1, 0, -4*v+4)

def gradient_rgb_wb_custom(v):
    if(v<0.143):
      return (1,-7*v+1,-7*v+1)
    elif(v>=0.143 and v<0.286):
      return (-7*v+2,7*v-1,0)
    elif(v>=0.286 and v<0.429):
      return (7*v-2,1,0)
    elif(v>0.429 and v<0.572):
      return (-7*v+4,1,7*v-3)
    elif(v>=0.572 and v<0.715):
      return (0,-7*v+5,1)
    elif(v>=0.715 and v<0.857):
      return (7*v-6,0,1)
    elif(v>0.857 and v<=1):
      return (-7*v+7,0,-7*v+7)

def gradient_hsv_bw(v):
    return hsv2rgb(0, 0, v)


def gradient_hsv_gbr(v):
    v = (0.333 + 0.666 * v) * 360
    return hsv2rgb(v, 1, 1)

def gradient_hsv_unknown(v):
    return hsv2rgb(135 - 130*v, 0.92, 0.92)


def gradient_hsv_custom(v):
  if v > 0.49:
    return hsv2rgb(359*v - 60, 0.95 - v/3, 1)
  if (v < 0.5 and v > 0.15):
    return hsv2rgb(340*v - 50, 0.7 + v/2, 1)
  else:
    return hsv2rgb(-359*v + 60, 0.7 + v/2, 1)

if __name__ == '__main__':
    def toname(g):
        return g.__name__.replace('gradient_', '').replace('_', '-').upper()

    gradients = (gradient_rgb_bw, gradient_rgb_gbr, gradient_rgb_gbr_full, gradient_rgb_wb_custom,
                 gradient_hsv_bw, gradient_hsv_gbr, gradient_hsv_unknown, gradient_hsv_custom)

    plot_color_gradients(gradients, [toname(g) for g in gradients])
