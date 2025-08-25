'use client';

import type { MimeType} from '@/constant/mime';
import { allImageMimeTypes } from '@/constant/mime';
import { fileSizeToReadableFormat } from '@/util/format';
import { useTranslations } from 'next-intl';
import type { ChangeEvent } from 'react';
import { toast } from 'sonner';

interface ImageSelectorProps {
  children: React.ReactNode;
  label?: string;
  onChange?: (image: File) => void;
  maximumAllowedSize?: number;
  allowedMimeTypes?: MimeType[];
}

export default function ImageSelector({
  children,
  label = 'image-selector',
  onChange,
  maximumAllowedSize = 1000000,
  allowedMimeTypes = allImageMimeTypes,
}: ImageSelectorProps) {
  const t = useTranslations();

  const fileUploadHandler = (e: ChangeEvent<HTMLInputElement>) => {
    const selectedFiles = e.target.files;
    if (!selectedFiles || selectedFiles.length === 0) {
      return;
    }

    const fileToUpload = selectedFiles[0];

    if (fileToUpload.size > maximumAllowedSize) {
      toast.error(
        t('error.file-too-large', {
          size: fileSizeToReadableFormat(maximumAllowedSize),
        }),
      );
      return;
    }

    if (!allowedMimeTypes.includes(fileToUpload.type as MimeType)) {
      toast.error(t('error.unsupported-file-type'));
      return;
    }

    if (onChange) {
      onChange(fileToUpload);
    }
  };

  return (
    <>
      <label htmlFor={label}>{children}</label>
      <input
        id={label}
        type='file'
        className='hidden'
        accept='image/*'
        onChange={fileUploadHandler}
      />
    </>
  );
}
