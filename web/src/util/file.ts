export function isValidFileFormat(files: FileList, supportedFormats: string[]) {
  if (files.length === 0) {
    return true;
  }

  for (const file of files) {
    const format = file.name.split('.').pop();
  }

  return false;
}
