'use client';

import JournalActions from './JournalActionsButton';
import ShareJournalButton from './ShareJournalButton';
import { EditableDiv } from '@/components/ui/editable';
import { Separator } from '@/components/ui/separator';
import { Skeleton } from '@/components/ui/skeleton';
import { useJournalQuery, useUpdateJournalMutation } from '@/query/journal';
import { useTranslations } from 'next-intl';

export default function JournalTitle({ journalId }: { journalId: string }) {
  const t = useTranslations();

  const { data } = useJournalQuery({ journalId });
  const { mutate: updateJournal } = useUpdateJournalMutation({ id: journalId });

  return (
    <div>
      <div className='h-15 mb-2 flex justify-between items-center'>
        {!data ? (
          <Skeleton className='h-full' />
        ) : (
          <EditableDiv
            onSubmit={(title) => {
              updateJournal({
                title,
              });
            }}
            initialContent={data.title}
            defaultContentOnEmpty={t('journal.main.title.empty')}
            className='text-4xl'
          />
        )}

        <div className='flex items-center'>
          <ShareJournalButton journalId={journalId} />
          <JournalActions />
        </div>
      </div>

      <Separator className='w-full h-0.5' />

      <EditableDiv
        initialContent='The only impossible journey is the one you never begin.'
        defaultContentOnEmpty=''
        className='italic my-5'
      />
    </div>
  );
}
