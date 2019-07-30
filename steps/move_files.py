import os
import shutil
from os import path
from os import listdir
from os.path import isfile, join


def move_files(source_directory, destination_directory):
    """
    Transfers contents of the source_directory to the destination_directory

    :param source_directory: Directory of the source contents
    :param destination_directory: Directory to move files into
    """
    
    files_in_source_dir = [f for f in listdir(source_directory) if isfile(join(source_directory, f))]
        
    for file in files_in_source_dir:
        shutil.copy(join(source_directory, file), destination_directory)

if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument(
        "source_directory",
        help="Directory of the source contents"
    )
    parser.add_argument(
        "destination_directory",
        help="Directory to move files into",
    )

    args = parser.parse_args()

    move_files(args.source_directory, args.destination_directory)
