import { Button } from '@/components/ui/button';
import { useTranslations } from 'next-intl';

export default function JournalCollectionWidgetPreview() {
  const t = useTranslations();

  return (
    <div className='h-full w-full flex flex-col'>
      <h1 className='self-center text-lg my-2'>Journal Collection</h1>
      <Button className='self-end'>
        {t('dashboard.widgets.journal-collection.add-journal')}
      </Button>
      <div></div>
    </div>
  );
}
