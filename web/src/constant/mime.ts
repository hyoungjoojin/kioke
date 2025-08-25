export const enum MimeType {
  APPLICATION_JSON = 'application/json',
  IMAGE_JPEG = 'image/jpeg',
  IMAGE_PNG = 'image/png',
}

export const allImageMimeTypes = [MimeType.IMAGE_JPEG, MimeType.IMAGE_PNG];

export function getMimeTypeFromDataUrl(url: string) {
  return url.substring(url.indexOf(':') + 1, url.indexOf(';')) as MimeType;
}
