import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Switch } from '@/components/ui/switch';
import useGetJournalByIdQuery from '@/hooks/query/useGetJournalByIdQuery';
import { useTranslations } from 'next-intl';

interface ShareJournalButtonProps {
  journalId: string;
  className?: string;
}

export default function ShareJournalButton({
  journalId,
  className,
}: ShareJournalButtonProps) {
  const t = useTranslations();

  const { data: journal, isPending } = useGetJournalByIdQuery({
    path: { journalId },
  });

  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button variant='icon' icon='share' className={className} />
      </PopoverTrigger>

      <PopoverContent className='h-96 flex flex-col' align='end'>
        <div className='grow'>
          <Input
            type='text'
            placeholder={t('journal.header.share.user-search.placeholder')}
          />

          <div></div>
        </div>

        <div className='self-end flex m-2 gap-1'>
          <Switch id='journal-visibility-toggle' checked={journal?.isPublic} />
          <Label htmlFor='journal-visibility-toggle'>Public</Label>
        </div>
      </PopoverContent>
    </Popover>
  );
}
