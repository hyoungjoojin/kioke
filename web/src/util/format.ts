export function fileSizeToReadableFormat(size: number) {
  const units = ['B', 'KB', 'MB', 'GB', 'TB'];

  let unit = 0;
  while (size >= 1000) {
    size /= 1000;
    unit += 1;
  }

  return `${size}${units[unit]}`;
}
