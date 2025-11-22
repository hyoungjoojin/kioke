'use client';

import { Separator } from '@/components/ui/separator';
import { Spinner } from '@/components/ui/spinner';
import { Routes } from '@/constant/routes';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import { usePageImagesQuery } from '@/query/page';
import { default as NextImage } from 'next/image';
import Link from 'next/link';

interface JournalGalleryViewParams {
  journalId: string;
}

function JournalGalleryView({ journalId }: JournalGalleryViewParams) {
  const { data: journal } = useGetJournalByIdQuery({ path: { journalId } });
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
    <div className='mb-5'>
      <div className='mb-2 flex items-center gap-2'>
        <Link href={Routes.PAGE(id)} className='hover:underline'>
          {title}
        </Link>
        <span className='text-sm'>{images && ` ${images.length} images`}</span>
      </div>

      {isPending && <Spinner />}

      {images ? (
        images.length === 0 ? (
          <div></div>
        ) : (
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
          })
        )
      ) : null}

      <Separator />
    </div>
  );
}

export default JournalGalleryView;
