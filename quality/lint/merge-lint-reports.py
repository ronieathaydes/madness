#!/usr/bin/env python

import os, fnmatch
from xml.etree import ElementTree

report_paths = []
merged_report_content = None

for root, directories, files in os.walk("."):
    for file in files:
        if fnmatch.fnmatch(file,'lint-results-debug.xml'):
            report_paths.append(os.path.join(root, file))

print('Found {amount} lint report files'.format(amount = len(report_paths)))

for path in report_paths:
    print(path)
    if os.path.isfile(path):
        data = ElementTree.parse(path).getroot()
        if merged_report_content is None:
            merged_report_content = data
        else:
            merged_report_content.extend(data)

merged_report_file_name = 'merged-lint-results-debug.xml'

merged_report = open(merged_report_file_name, 'w')
merged_report.write('<?xml version=\"1.0\" encoding=\"utf-8\"?>\n')
if merged_report_content is not None:
    merged_report.write(ElementTree.tostring(merged_report_content, encoding='unicode', method='xml'))
merged_report.close()

print('Reports merged into')
print(merged_report_file_name)
