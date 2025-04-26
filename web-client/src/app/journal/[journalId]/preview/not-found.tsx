import Link from 'next/link';

export default function NotFound() {
  return (
    <main className='w-screen h-screen flex items-center justify-center'>
      <section className='bg-white dark:bg-gray-900'>
        <div className='py-8 px-4 mx-auto max-w-screen-xl lg:py-16 lg:px-6'>
          <div className='mx-auto max-w-screen-sm text-center'>
            <h1 className='mb-4 text-7xl tracking-tight font-extrabold lg:text-9xl text-primary-600 dark:text-primary-500'>
              404
            </h1>
            <p className='mb-4 text-lg font-light text-gray-500 dark:text-gray-400'>
              The journal can't be found.
            </p>
            <Link
              href='/'
              className='inline-flex font-medium rounded-lg px-5 py-2.5 text-center my-4'
            >
              Back to Homepage
            </Link>
          </div>
        </div>
      </section>
    </main>
  );
}
