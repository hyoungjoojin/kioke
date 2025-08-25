'use client';

import { Calendar } from '@/components/ui/calendar';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuPortal,
  DropdownMenuSub,
  DropdownMenuSubContent,
  DropdownMenuSubTrigger,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Routes } from '@/constant/routes';
import { useJournalQuery } from '@/query/journal';
import { useUpdatePageMutation } from '@/query/page';
import { Ellipsis } from 'lucide-react';
import { useTranslations } from 'next-intl';
import { redirect } from 'next/navigation';

interface PagesListProps {
  journalId: string;
}

export default function PagesList({ journalId }: PagesListProps) {
  const { data } = useJournalQuery({
    journalId,
  });

  return (
    <div className='pl-3 pr-10'>
      {data &&
        data.pages.map((page, index) => {
          return (
            <PagesListItem
              key={index}
              journalId={journalId}
              pageId={page.id}
              title={page.title}
              date={new Date()}
            />
          );
        })}
    </div>
  );
}

function PagesListItem({
  journalId,
  pageId,
  title,
  date,
}: {
  journalId: string;
  pageId: string;
  title: string;
  date: Date;
}) {
  const t = useTranslations();

  const { mutate: updatePage } = useUpdatePageMutation({
    id: pageId,
  });

  return (
    <div className='px-2 my-2 flex items-center justify-between rounded-md hover:bg-accent'>
      <div
        onClick={() => {
          redirect(Routes.PAGE(pageId));
        }}
        className='hover:underline hover:cursor-pointer'
      >
        <span>{title || t('journal.list.item.title.empty')}</span>
      </div>

      <DropdownMenu>
        <DropdownMenuTrigger>
          <Ellipsis size={16} />
        </DropdownMenuTrigger>

        <DropdownMenuContent>
          <DropdownMenuSub>
            <DropdownMenuSubTrigger>
              {t('journal.list.item.actions.change-date')}
            </DropdownMenuSubTrigger>
            <DropdownMenuPortal>
              <DropdownMenuSubContent>
                <Calendar
                  month={date}
                  selected={date}
                  mode='single'
                  onSelect={(date) => {
                    if (date) {
                      updatePage({
                        journalId,
                        date,
                      });
                    }
                  }}
                />
              </DropdownMenuSubContent>
            </DropdownMenuPortal>
          </DropdownMenuSub>
          <DropdownMenuItem>
            {t('journal.list.item.actions.delete')}
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  );
}
