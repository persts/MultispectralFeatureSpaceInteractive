##Multispectral Feature Space Interactive
The feature space interactive is designed to teach basic concepts about what feature space is and how it
is used in image classification. When we use the term “feature” in the context of “feature space” we refer
to data layers that comprise a data set. When the data set is a multispectral image the features are the
individual bands. Other data layers, such as elevation, slope, aspect can also be features in a data set. A
subset of a Landsat image will be used for the interactive but the same concepts apply regardless of the
type of features.

Data in an image are arranged as a grid of pixels. When we look at a satellite image the pixels are organized
in a grid so that objects such as lakes, rivers, and forests appear in a way similar to the way they would
in a photograph or on a map. We can also view image data using a feature space reference system. In a
feature space plot the axes represent the range of possible values for a specific feature. In our case the axes
represent the range of pixel values which for Landsat images range from 0 to 255. The simplest feature
space plot uses two axes (X and Y) and this is what is used in this interactive. Three-dimensional plots
are also possible for visualization purposes. When used in an image classification algorithm the feature
space can have several dimensions, one dimension for each feature. In other words if you are processing
a 7 band image the algorithm will be working in 7-dimensional feature space.

![Screen Shot](/img/screenshot.jpg)
