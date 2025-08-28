import type { JournalCollectionWidgetContent } from '.';
import { Button } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Spinner } from '@/components/ui/spinner';
import { Routes } from '@/constant/routes';
import { useCollectionQuery } from '@/query/collection';
import { useCreateJournalMutation } from '@/query/journal';
import { useTranslations } from 'next-intl';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export default function JournalCollectionWidget({
  collectionId = '',
}: JournalCollectionWidgetContent) {
  const t = useTranslations();
  const router = useRouter();

  const {
    data: collection,
    error: collectionQueryError,
    isError: isCollectionQueryError,
    isPending: isCollectionQueryPending,
  } = useCollectionQuery(
    { id: collectionId },
    {
      enabled: collectionId !== '',
    },
  );

  const {
    data: createdJournal,
    mutate: createJournal,
    isPending: isCreateJournalPending,
    isSuccess: isCreateJournalSuccess,
  } = useCreateJournalMutation({ collectionId });

  useEffect(() => {
    if (isCreateJournalSuccess && createdJournal) {
      router.push(Routes.JOURNAL(createdJournal.id));
    }
  }, [router, createdJournal, isCreateJournalSuccess]);

  if (isCollectionQueryError) {
    if (!collectionQueryError) {
      return null;
    }

    return <div>Collection Not Found</div>;
  }

  return (
    <div>
      {isCollectionQueryPending ? (
        <Spinner />
      ) : (
        collection && (
          <>
            <div className='grow-0 text-center mb-3'>
              <span className='text-lg'>{collection.name}</span>
            </div>

            <div className='grow-0 self-end'>
              <Button
                pending={isCreateJournalPending}
                onClick={() => {
                  if (isCreateJournalPending) {
                    return;
                  }

                  createJournal({
                    collectionId,
                    title: t('default-values.initial-journal-title'),
                  });
                }}
              >
                {t('dashboard.widgets.journal-collection.add-journal')}
              </Button>
            </div>

            <ScrollArea className='mt-2 grow w-full overflow-hidden'>
              {collection.journals.map((journal, index) => {
                return (
                  <Journal
                    key={index}
                    journalId={journal.id}
                    title={journal.title}
                  />
                );
              })}
            </ScrollArea>
          </>
        )
      )}
    </div>
  );
}

function Journal({ journalId, title }: { journalId: string; title: string }) {
  return (
    <div>
      <Link href={Routes.JOURNAL(journalId)}>
        <span className='hover:underline hover:cursor-pointer'>{title}</span>
      </Link>
    </div>
  );
}
