'use client';

import { Spinner } from '@/components/ui/spinner';
import { Routes } from '@/constant/routes';
import { useJournalQuery } from '@/query/journal';
import { usePageImagesQuery } from '@/query/page';
import { default as NextImage } from 'next/image';
import Link from 'next/link';

interface JournalGalleryViewParams {
  journalId: string;
}

function JournalGalleryView({ journalId }: JournalGalleryViewParams) {
  const { data: journal } = useJournalQuery({ journalId });
  if (!journal) {
    return null;
  }

  return (
    <>
      {journal.pages.map((page, index) => {
        return <Page key={index} {...page} />;
      })}
    </>
  );
}

interface PageProps {
  id: string;
  title: string;
}

function Page({ id, title }: PageProps) {
  const { data: images, isPending } = usePageImagesQuery({ id });

  return (
    <>
      <Link href={Routes.PAGE(id)} className='hover:underline'>
        {title}
      </Link>

      {isPending && <Spinner />}

      {images &&
        images.map((image, index) => {
          return (
            <NextImage
              key={index}
              alt={image.description || ''}
              src={image.url}
              width={image.width}
              height={image.height}
            />
          );
        })}
    </>
  );
}

export default JournalGalleryView;
