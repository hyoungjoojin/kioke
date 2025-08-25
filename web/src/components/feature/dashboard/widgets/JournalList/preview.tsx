import { Button } from '@/components/ui/button';
import { IconName } from '@/components/ui/icon';

export default function JournalListPreview() {
  return (
    <div className='flex flex-col'>
      <h1>Collection Name</h1>
      <Button className='self-end' icon={IconName.PLUS}>
        Add
      </Button>
      <div></div>
    </div>
  );
}
