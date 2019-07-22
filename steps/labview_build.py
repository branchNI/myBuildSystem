import os
import re
import shutil
import stat
import subprocess
import tempfile
import traceback
from contextlib import contextmanager
from os import path


def build_lv_project(project_path, target_name, build_spec_name, lv_version, lv_bitness):
    """
    Executes the specified build spec for the specified project.

    :param project_path: Path to the LabVIEW project to be built
    :param target_name: Name of the target in the project containing the build specification
    :param build_spec_name: Name of the build specification to be built
    :param lv_version: The year version of LabVIEW to use for diffing
    :param lv_bitness: Bitness of LabVIEW (either "32" or "64")
    """

    version_path = labview_path_from_year(lv_version, lv_bitness)

    command_args = [
        "LabVIEWCLI.exe",
        "-LabVIEWPath", version_path,
        "-OperationName", "ExecuteBuildSpec",
        "-ProjectPath", project_path,
        "-BuildSpecName",
    ]

    subprocess.call(["taskkill", "/IM", "labview.exe", "/F"])
    try:
        subprocess.check_call(command_args)
    except subprocess.CalledProcessError:
        print("Failed to build project \"{0}\". \n\tBuild spec name: \"{1}\"\n\tTarget:name: \"{2}\"".format(project_path, build_spec_name, target_name))
        traceback.print_exc()

def labview_path_from_year(year, bitness):
    env_key = "labviewPath_" + str(year)
    if env_key in os.environ:
        return os.environ[env_key]

    if bitness == "32":
        return r"{0}\National Instruments\LabVIEW {1}\LabVIEW.exe".format(os.environ["ProgramFiles(x86)"], year)
    elif bitness == "64":
        return r"{0}\National Instruments\LabVIEW {1}\LabVIEW.exe".format(os.environ["ProgramFiles"], year)
    else:
        return None

if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument(
        "project_path",
        help="Path to the LabVIEW project to be built"
    )
    parser.add_argument(
        "target_name",
        help="Name of the target in the project containing the build specification",
        default="My Computer"
    )
    parser.add_argument(
        "build_spec_name",
        help="Name of the build specification to be built"
    )
    parser.add_argument(
        "labview_version",
        help="The year version of LabVIEW to use for diffing"
    )
    parser.add_argument(
        "labview_bitness",
        help="Bitness of LabVIEW (either \"32\" or \"64\")"
    )

    args = parser.parse_args()

    build_lv_project(args.project_path, args.target_name, args.build_spec_name, args.labview_version, args.labview_bitness)
