import { usePageImagesQuery } from '@/query/image';
import { useJournalQuery } from '@/query/journal';
import Image from 'next/image';

interface GalleryJournalContentProps {
  journalId: string;
}

export function GalleryJournalContent({
  journalId,
}: GalleryJournalContentProps) {
  const { data: journal } = useJournalQuery({ journalId });

  if (!journal) {
    return null;
  }

  return (
    <>
      {journal.pages.map((page, index) => {
        return <PageContent title={page.title} pageId={page.id} key={index} />;
      })}
    </>
  );
}

function PageContent({ title, pageId }: { title: string; pageId: string }) {
  const { data: images } = usePageImagesQuery(pageId);

  if (!images) {
    return null;
  }

  return (
    <div>
      <span>{title}</span>
      {images.map((image, index) => {
        return (
          <div key={index}>
            <Image alt='' src={image} width={200} height={100} />
          </div>
        );
      })}
    </div>
  );
}
