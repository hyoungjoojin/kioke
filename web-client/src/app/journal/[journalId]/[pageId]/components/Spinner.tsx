import { RingSpinner } from '@/components/ui/spinner';
import { useTransactionStatus } from '@/hooks/store/transaction';
import { TransactionStatus } from '@/store/transaction';

export default function Spinner() {
  const status = useTransactionStatus();

  return (
    <div className='flex items-center justify-center'>
      <RingSpinner loading={status === TransactionStatus.SAVING} />
      {status === TransactionStatus.SAVING && (
        <span className='mx-1 text-sm'>Saving...</span>
      )}
    </div>
  );
}
