# Shredder

A simple tool to make uploads to [paperless][1] a bit
easier. It works by iterating over some files, and asking
some data about them. For each file, it then generates a
filename so that paperless can [extract the information][2]
from that name.

For now it can only read from, and write to files on your
local machine. In the future I'll add support for reading
files from Dropbox and sending files straight to paperless
over its API.

[1]: https://github.com/danielquinn/paperless
[2]: https://paperless.readthedocs.io/en/latest/guesswork.html
