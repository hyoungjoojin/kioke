'use client';

import { Button } from '@/components/ui/button';
import { useCreatePageMutationQuery } from '@/query/page';
import { useTranslations } from 'next-intl';

interface CreatePageButtonProps {
  journalId: string;
}

export default function CreatePageButton({ journalId }: CreatePageButtonProps) {
  const t = useTranslations();

  const { mutate: createPage, isPending } = useCreatePageMutationQuery();

  return (
    <Button
      size='sm'
      pending={isPending}
      onClick={() => {
        createPage({
          journalId,
          title: t('default-values.initial-journal-title'),
          date: new Date(),
        });
      }}
    >
      Add
    </Button>
  );
}
