function showDiv(id) {
    const divs = document.getElementsByTagName('div');
    for (let i = 0; i < divs.length; i++) {
        if (divs[i].id === 'checkSpam' || divs[i].id === 'train') {
            divs[i].style.display = 'none';
        }
    }
    document.getElementById(id).style.display = 'block';
}

window.onload = function () {
    showDiv('checkSpam');
}

function submitCheckSpamForm() {
    const form = document.getElementById('checkSpamForm');
    const content = form.querySelector('textarea[name="content"]').value;

    fetch('/check-spam', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content: content })
    })
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById('spamCheckResult');
            resultDiv.innerHTML = '';

            const messageDiv = document.createElement('div');
            const spamProbabilityDiv = document.createElement('div');
            const impactPercentagesDiv = document.createElement('div');

            messageDiv.classList.add('result-message');
            spamProbabilityDiv.classList.add('result-spamProbability');
            impactPercentagesDiv.classList.add('result-impactPercentages');

            messageDiv.textContent = 'Message: ' + data.message;
            spamProbabilityDiv.textContent = 'Spam Probability: ' + data.spamProbability;

            if (data.spamProbabilityResult && data.spamProbabilityResult.wordImpactPercentages) {
                const impactList = document.createElement('ul');
                for (const [word, percentage] of Object.entries(data.spamProbabilityResult.wordImpactPercentages)) {
                    const listItem = document.createElement('li');
                    listItem.textContent = `${word}: ${percentage.toFixed(2)}%`;
                    impactList.appendChild(listItem);
                }
                impactPercentagesDiv.appendChild(impactList);
            } else {
                impactPercentagesDiv.textContent = 'No word impact percentages available.';
            }

            resultDiv.appendChild(messageDiv);
            resultDiv.appendChild(spamProbabilityDiv);
            resultDiv.appendChild(impactPercentagesDiv);
        })
        .catch(error => console.error('Error:', error));
}

function submitTrainForm() {
    const form = document.getElementById('trainForm');
    const content = form.querySelector('textarea[name="content"]').value;

    fetch('/train-spam', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content: content })
    })
        .then(response => {
            if (response.ok) {
                document.getElementById('trainResult').innerText = 'Training successful';
            } else {
                document.getElementById('trainResult').innerText = 'Training failed';
            }
        })
        .catch(error => console.error('Error:', error));
}

document.addEventListener('DOMContentLoaded', (event) => {

    const navbarItems = document.querySelectorAll('.navbar a');
    const checkSpam = document.querySelector('.navbar a[href="#checkSpam"]');
    const train = document.querySelector('.navbar a[href="#train"]');

    checkSpam.style.color = '#94a596';
    checkSpam.style.background = '#003b49';

    navbarItems.forEach(item => {
        item.addEventListener('click', function() {
            checkSpam.style.background = '#94a596';
            checkSpam.style.color = '#003b49';
            train.style.background = '#94a596';
            train.style.color = '#003b49';

            this.style.color = '#94a596';
            this.style.background = '#003b49';

            if (this === checkSpam) {
                train.style.background = '#94a596';
                train.style.color = '#003b49';
            } else {
                checkSpam.style.background = '#94a596';
                checkSpam.style.color = '#003b49';
            }
        });
    });

    document.addEventListener('DOMContentLoaded', (event) => {
        document.querySelector('#checkSpam input[type="submit"]').addEventListener('click', function (event) {
            event.preventDefault();

            const content = document.querySelector('#checkSpam textarea[name="content"]').value;
            const resultDiv = document.getElementById('spamCheckResult');

            fetch('/check-spam', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({content: content}),
            })
                .then(response => response.json())
                .then(data => {
                    resultDiv.innerHTML = '';

                    const messageDiv = document.createElement('div');
                    const spamProbabilityDiv = document.createElement('div');
                    const impactPercentagesDiv = document.createElement('div');

                    messageDiv.classList.add('result-message');
                    spamProbabilityDiv.classList.add('result-spamProbability');
                    impactPercentagesDiv.classList.add('result-impactPercentages');

                    messageDiv.textContent = 'Message: ' + data.message;
                    spamProbabilityDiv.textContent = 'Spam Probability: ' + data.spamProbability;

                    if (data.spamProbabilityResult && data.spamProbabilityResult.topWordImpactPercentages) {
                        const impactList = document.createElement('ul');
                        for (const [word, percentage] of Object.entries(data.spamProbabilityResult.topWordImpactPercentages)) {
                            const listItem = document.createElement('li');
                            listItem.textContent = `${word}: ${percentage.toFixed(2)}%`;
                            impactList.appendChild(listItem);
                        }
                        impactPercentagesDiv.appendChild(impactList);
                    } else {
                        impactPercentagesDiv.textContent = 'No word impact percentages available.';
                    }

                    resultDiv.appendChild(messageDiv);
                    resultDiv.appendChild(spamProbabilityDiv);
                    resultDiv.appendChild(impactPercentagesDiv);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        });
    });

    document.querySelector('#train form').addEventListener('submit', function (event) {
        event.preventDefault();

        const contentTextArea = document.querySelector('#train textarea[name="content"]');

        const content = contentTextArea.value;

        fetch('/train-spam', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                content: content,
            }),
        }).then(function(response) {
            if (response.status === 200) {
                contentTextArea.value = '';

                const resultDiv = document.getElementById('trainResult');
                resultDiv.innerHTML = '';

                const messageDiv = document.createElement('div');
                messageDiv.textContent = 'Training was successful';
                resultDiv.appendChild(messageDiv);
            }
        })
            .catch((error) => {
                console.error('Error:', error);
            });
    });
});
