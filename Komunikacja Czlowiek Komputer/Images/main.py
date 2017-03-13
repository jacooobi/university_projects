import matplotlib.pyplot as plt
import os
from skimage import morphology, data
from skimage.filters import sobel
import numpy as np

IMAGE_DIR = './images/'
OUTPUT_FILENAME = 'result.jpg'

def transform_image(image):
    return morphology.dilation(sobel(image))


def transform(images):
    for index, image in enumerate(images):
        yield transform_image(image)

def get_images(image_dir):

    for image_filename in os.listdir(IMAGE_DIR):
        abs_image_filename = os.path.abspath(os.path.join(IMAGE_DIR, image_filename))
        yield data.imread(abs_image_filename, as_grey=True)

def plot_images(images):
    fig = plt.figure(num=None, figsize=(8, 8), dpi=100, facecolor='black')

    for index, image in enumerate(images):
        a = fig.add_subplot(3, 2, index + 1)
        image = 255 - image
        plt.imshow(image, cmap='Greys')
        plt.axis("off")

    fig.savefig(OUTPUT_FILENAME, facecolor=fig.get_facecolor())
    plt.close('all')

def main():
    plot_images(transform(get_images(IMAGE_DIR)))

if __name__ == "__main__":
    main()
