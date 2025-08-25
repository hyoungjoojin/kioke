import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Switch } from '@/components/ui/switch';
import { useJournalQuery, useUpdateJournalMutation } from '@/query/journal';
import { useTranslations } from 'next-intl';

interface ShareJournalButtonProps {
  journalId: string;
}

export default function ShareJournalButton({
  journalId,
}: ShareJournalButtonProps) {
  const t = useTranslations();

  const { data: journal, isPending } = useJournalQuery({ journalId });
  const { mutate: updateJournal, isPending: isUpdateJournalPending } =
    useUpdateJournalMutation({
      id: journalId,
    });

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='outline'>Share</Button>
      </DropdownMenuTrigger>

      <DropdownMenuContent align='end'>
        <div className='h-96 w-96 flex flex-col'>
          <div className='grow'>
            <Input
              type='text'
              placeholder={t('journal.header.share.user-search.placeholder')}
            />

            <div></div>
          </div>

          <div className='self-end flex m-2 gap-1'>
            <Switch
              id='journal-visibility-toggle'
              checked={journal?.isPublic}
              disabled={isPending || isUpdateJournalPending}
              onCheckedChange={(checked) => {
                updateJournal({
                  isPublic: checked,
                });
              }}
            />
            <Label htmlFor='journal-visibility-toggle'>Public</Label>
          </div>
        </div>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
