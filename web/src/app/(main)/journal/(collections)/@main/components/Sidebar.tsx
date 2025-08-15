import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { IconName } from '@/components/ui/icon';
import { Routes } from '@/constant/routes';
import { useClickOutside } from '@/hooks/useClickOutside';
import { cn } from '@/lib/utils';
import { useCreateJournalMutationQuery } from '@/query/journal';
import { JournalCollection } from '@/types/journal';
import { useTranslations } from 'next-intl';
import Link from 'next/link';
import { useRef } from 'react';

interface SidebarProps {
  collection: JournalCollection;
  onClose?: () => void;
}

export default function Sidebar({ collection, onClose }: SidebarProps) {
  const t = useTranslations();

  const sidebarRef = useRef(null);

  const { mutate: createJournal, isPending: isCreateJournalPending } =
    useCreateJournalMutationQuery(collection.id);

  useClickOutside(sidebarRef, () => {
    if (onClose) {
      onClose();
    }
  });

  return (
    <Card
      className={cn(
        'rounded-2xl',
        'h-56',
        'max-md:absolute max-md:left-0 max-md:w-full',
        'w-96 lg:w-[40rem] xl:w-[64rem]',
      )}
      ref={sidebarRef}
    >
      <CardContent>
        <h1 className='text-2xl'>{collection.name}</h1>
        <Button
          onClick={() => {
            createJournal({
              title: t('default-values.initial-journal-title'),
            });
          }}
          pending={isCreateJournalPending}
          icon={IconName.PLUS}
          variant='ghost'
        >
          {t('journal-collections.sidebar.add-new-journal')}
        </Button>

        {collection.journals.map((journal, index) => {
          return (
            <div className='hover:underline' key={index}>
              <Link href={Routes.JOURNAL(journal.journalId)}>
                {journal.title}
              </Link>
            </div>
          );
        })}
      </CardContent>
    </Card>
  );
}
