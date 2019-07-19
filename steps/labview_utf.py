import os
import re
import shutil
import stat
import subprocess
import tempfile
import traceback
from contextlib import contextmanager
from os import path


def run_unit_tests(utf_project_path, report_path, lv_version ):
    """
    Performs unit tests on a LabVIEW project.

    :param utf_project_path: Directory of the project to be unit tested
    :param report_path: Directory to generate unit test reports to
    :param lv_version: The year version of LabVIEW to use for unit testing
    """

    version_path = labview_path_from_year(lv_version)

    command_args = [
        "LabVIEWCLI.exe",
        "-LabVIEWPath", version_path,
        "-OperationName", "RunUnitTests",
        "-ProjectPath", utf_project_path,
        "-JUnitReportPath", report_path,
    ]
    
    subprocess.call(["taskkill", "/IM", "labview.exe", "/F"])
    
    try:
        subprocess.check_call(command_args)
    except subprocess.CalledProcessError:
        print("Failed to perform unit tests on \"{0}\"".format(utf_project_path))
        traceback.print_exc()


def labview_path_from_year(year):
    env_key = "labviewPath_" + str(year)
    if env_key in os.environ:
        return os.environ[env_key]

    return r"{0}\National Instruments\LabVIEW {1}\LabVIEW.exe".format(os.environ["ProgramFiles(x86)"], year)


if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument(
        "utf_project_path",
        help="Directory of the project to be unit tested"
    )
    parser.add_argument(
        "report_path",
        help="Directory to generate unit test reports to"
    )
    parser.add_argument(
        "labview_version",
        help="Year version of LabVIEW you wish to use for unit testing"
    )

    args = parser.parse_args()

    run_unit_tests(args.utf_project_path, args.report_path, args.labview_version)
